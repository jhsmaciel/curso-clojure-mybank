(ns mybank-web-api.db
  (:require [com.stuartsierra.component :as component]))

(defonce accounts (atom {:1 {:saldo 100}
                         :2 {:saldo 200}
                         :3 {:saldo 300}}))
(defn reset-db
  []
  (reset! accounts {:1 {:saldo 100}
                    :2 {:saldo 200}
                    :3 {:saldo 300}}))


(defrecord Database []
  component/Lifecycle
  (start [this]
    (assoc this :accounts accounts))


  (stop [this]
    (reset-db)
    (assoc this :accounts nil)))

(defn new-database []
  (->Database))
