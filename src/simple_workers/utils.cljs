(ns simple-workers.utils
  (:require
   [cognitect.transit :as t]))

(defn deserialize
  [data]
  (let [r (t/reader :json)]
    (t/read r data)))

(defn serialize
  [data]
  (let [w (t/writer :json)]
    (t/write w data)))

(defn post-data [inst message ]
  (let [message (serialize message)]
    (.postMessage inst message)))

(defn event-data [event]
  (-> (.-data event)
      (deserialize)))

(defn handle-error [inst e]
  (when-let [c js/console]
    (.error c e))
  (let [message   {:state   :error
                   :message (.toString e)}]

    (post-data inst message )))