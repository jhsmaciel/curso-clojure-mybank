(ns mybank-web-api.interceptor.account
  (:require
   [io.pedestal.interceptor :as i]
   [mybank-web-api.db :as m.db]))

(defn add-accounts-db-atom [context]
  (update context :request assoc :accounts m.db/accounts))

(def accounts-interceptor
  (i/interceptor {:name  ::accounts-interceptor
                  :enter add-accounts-db-atom}))
