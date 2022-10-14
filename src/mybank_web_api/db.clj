(ns mybank-web-api.db)

(defonce accounts (atom {:1 {:saldo 100}
                         :2 {:saldo 200}
                         :3 {:saldo 300}}))
(defn reset-db
  []
  (reset! accounts {:1 {:saldo 100}
                    :2 {:saldo 200}
                    :3 {:saldo 300}}))
