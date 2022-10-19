(ns mybank-web-api.server
  (:require [io.pedestal.http :as http]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defonce server (atom nil))

(defn start [service-map]
  (reset! server (http/start (http/create-server
                               service-map))))

(defn stop []
  (http/stop @server))

(defn restart-or-start
  [service-map]
  (try
    (stop)
    (catch Exception _)
    (finally (start service-map))))

(defrecord Server [database routes interceptors]
  component/Lifecycle
  (start [this]
    (let [service-map-base {::http/routes (-> routes :routes)
                            ::http/port   9999
                            ::http/type   :jetty
                            ::http/join?  false}
          service-map (-> service-map-base
                          (http/default-interceptors)
                          #_(update ::http/interceptors concat (-> interceptors :interceptors)))]
      (restart-or-start service-map)
      this))

  (stop [this]
    (stop)
    this))

(defn new-server []
  (->Server {} {} {}))
