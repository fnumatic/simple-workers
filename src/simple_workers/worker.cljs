(ns simple-workers.worker
  (:require [cljs.core.async :refer [<!]]
            [cljs.core.async.impl.protocols :as asyncp]
            [simple-workers.utils :as wu])

  (:require-macros [cljs.core.async.macros :refer [go]]))

(def handlers
  (atom {}))

(defn register  [id fun]
  (swap! handlers assoc id fun))

(defn- chan?  [x]
  (satisfies? asyncp/ReadPort x))

(defn- do-respond!  [data]
  (try
    (let [message  {:data data 
                    :state :success} ]

      (wu/post-data js/self message ))

    (catch js/Object e
      (wu/handle-error js/self e))))

(defn- handle-request! [{:keys [handler arguments] } ]
  (try
    (let [handler (@handlers handler)
          result  (handler arguments)  ]

      (if (chan? result)
        (go (do-respond! (<! result)))
        (do-respond! result)))

    (catch js/Object e
      (wu/handle-error js/self e))))

(defn bootstrap []
  (aset js/self "onmessage" (comp handle-request! wu/event-data)))