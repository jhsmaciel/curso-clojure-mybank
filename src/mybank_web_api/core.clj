(ns mybank-web-api.core
  (:require
   [io.pedestal.http :as http]
   [io.pedestal.test :as test-http]
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

(defn test-request [server verb url]
  (test-http/response-for (::http/service-fn @server) verb url))
(defn test-post [server verb url body]
  (test-http/response-for (::http/service-fn @server) verb url :body body))

(comment
  (start)
  (http/stop @server)


  (test-request server :get "/saldo/1")
  (test-request server :get "/saldo/2")
  (test-request server :get "/saldo/3")
  (test-request server :get "/saldo/4")
  (test-post server :post "/deposito/1" "199.93")
  (test-post server :post "/deposito/4" "325.99")

  ;curl http://localhost:9999/saldo/1
  ;curl -d "199.99" -X POST http://localhost:9999/deposito/1
  )
