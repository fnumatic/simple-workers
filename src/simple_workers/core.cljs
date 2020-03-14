(ns simple-workers.core
  (:require [cljs.core.async :refer [ promise-chan  put!]]
            [simple-workers.utils :as wu]))

(defn supported?  []
  (-> js/self
      .-Worker
      undefined?
      not))

(defn worker? []
  (-> js/self
      .-document
      undefined?))

(def main?
  (complement worker?))

(defn create-one [script]
  (js/Worker. script))

(defn- do-request! [worker {:keys [handler arguments ]}]
  (let [message  {:handler   handler
                  :arguments arguments}]

    (wu/post-data worker message )))


(defn do-with-worker!  [worker request]
  (let [result
        (promise-chan)

        put-result!
        (partial put! result)]

    (->> (comp put-result! wu/event-data)
         (aset worker "onmessage"))

    (try
      (do-request! worker request)
      (catch js/Object e
        (put! result {:state :error, :error e})))

    result))