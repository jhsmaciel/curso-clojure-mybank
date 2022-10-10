(ns mybank-web-api.interceptors.error
  (:require [io.pedestal.interceptor :as i]))

(defonce contas (atom {:1 {:saldo 100}
                       :2 {:saldo 200}
                       :3 {:saldo 300}}))

(defn add-contas-atom [context]
  (update context :request assoc :contas contas))

(def contas-interceptor
  (i/interceptor {:name  ::contas-interceptor
                 :enter add-contas-atom}))
(ns mybank-web-api.interceptors.default)
