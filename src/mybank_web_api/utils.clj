(ns mybank-web-api.utils
  (:require [io.pedestal.test :as test-http]
            [io.pedestal.http :as http]))

(defn test-request [server verb url]
  (test-http/response-for (::http/service-fn @server) verb url
                          :headers {"Accept" "application/json"
                                    "Content-Type" "application/json"}))

(defn test-post [server verb url body]
  (test-http/response-for (::http/service-fn @server) verb url
                          :headers {"Accept" "application/json"
                                    "Content-Type" "application/json"} :body body))
