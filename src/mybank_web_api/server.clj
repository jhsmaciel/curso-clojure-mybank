(ns mybank-web-api.server
  (:require [io.pedestal.http :as http]
            [mybank-web-api.routes :refer [routes]])
  (:gen-class))

(defonce server (atom nil))

(def service-map
  (http/default-interceptors
   {::http/routes routes
    ::http/port   9999
    ::http/type   :jetty
    ::http/join?  false}))

(defn create-server []
  (http/create-server
   service-map))

(defn start []
  (reset! server (http/start (create-server))))

(defn reset-server
  []
  (try
    (http/stop @server)
    (catch Exception _)
    (finally (start))))
