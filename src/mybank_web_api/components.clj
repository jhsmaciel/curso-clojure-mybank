(ns mybank-web-api.components
  (:require
   [com.stuartsierra.component :as component]
   [mybank-web-api.db :as m.db]
   [mybank-web-api.interceptor.interceptors :as m.interceptor.interceptors]
   [mybank-web-api.routes :as m.routes]
   [mybank-web-api.server :as m.server]))
(def system-map-components (atom nil))

(def system-map
  (component/system-map
    :database (component/using (m.db/new-database) [])
    :inteceptors (component/using (m.interceptor.interceptors/new-interceptors) [])
    :routes (component/using (m.routes/new-routes) [])
    :web-server (component/using (m.server/new-server) [:database :routes :inteceptors])))


(defn main [] (reset! system-map-components (component/start system-map)))
