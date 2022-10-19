(ns mybank-web-api.interceptor.header
  (:require
   [clojure.data.json :as json]
   [io.pedestal.http.content-negotiation :as conneg]
   [io.pedestal.interceptor :as i]))

(def supported-types ["text/html" "application/edn" "application/json" "text/plain"])

(def supported-types-interceptor (conneg/negotiate-content supported-types))


(defn accepted-type
  [context]
  (get-in context [:request :accept :field] "text/plain"))

(defn transform-content
  [body content-type]
  (case content-type
    "text/html"        body
    "text/plain"       body
    "application/edn"  (pr-str body)
    "application/json" (json/write-str body)))

(defn coerce-to
  [response content-type]
  (-> response
      (update :body transform-content content-type)
      (assoc-in [:headers "Content-Type"] content-type)))

(defn coerce-body
  [context]
  (if (get-in context [:response :headers "Content-Type"])
    context
    (update-in context [:response] coerce-to (accepted-type context))))

(def coerce-body-interceptor
  (i/interceptor {:name ::coerce-body
                  :leave coerce-body}))
