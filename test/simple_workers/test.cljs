(ns simple-workers.test
  (:require [cljs.core.async :refer [<!]]
            [simple-workers.core :as main]
            )
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn app
  []
  (let [worker       (main/create-one  "js/worker.js")

        print-result (fn [result-chan]
                       (go
                         (let [result (<! result-chan)]
                           (.debug js/console
                                   (str (:state result))
                                   result))))]

    (print-result (main/do-with-worker! worker {:handler   :mirror
                                                :arguments {:a "hello"
                                                            :b "world"
                                                            :c 10}}))
    
    ))


(defn ^:dev/after-load init []
  (app))
