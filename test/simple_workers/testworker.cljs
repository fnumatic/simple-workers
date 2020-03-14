(ns simple-workers.testworker
  (:require 
            [simple-workers.worker :as worker])  )

(defn worker
  []
  (worker/register
   
   :mirror identity
   )

  (worker/bootstrap))

(worker)