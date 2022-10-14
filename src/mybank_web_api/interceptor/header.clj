(ns mybank-web-api.interceptor.header
  (:require [io.pedestal.interceptor :as i]))

(defn add-content-type-header
  [context]
  (assoc-in context [:response :headers "Content-Type"] "text/plain"))

(def content-type-header-interceptor
  (i/interceptor {:name ::content-type-header
                  :leave add-content-type-header}))