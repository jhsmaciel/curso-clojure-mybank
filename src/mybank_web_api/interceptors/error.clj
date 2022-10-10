(ns mybank-web-api.interceptors.error
  (:require [io.pedestal.interceptor :as i]))

(defn catch-error
  [context error]
  (println "----------------------------------------------------------------------------------")
  (println context)
  (println (ex-data error))
  (let [{:keys [type]} (ex-data error)]
    (assoc context :response { :status 400 :body { :message (.getLocalizedMessage error) :type type}})))

(def error-interceptor
  (i/interceptor {:name ::catch-erro
                  :error catch-error}))
