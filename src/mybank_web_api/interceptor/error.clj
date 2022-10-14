(ns mybank-web-api.interceptor.error
  (:require [io.pedestal.interceptor :as i]))

(defn get-status-by-type
  [type]
  (cond
    (= type :mybank-web-api.logic.account/account-without-balance) 412
    (= type :mybank-web-api.logic.account/account-not-found) 400
    :else 500))

(defn catch-error
  [context error]
  (println "----------------------------------------------------------------------------------")
  (println context)
  (println (ex-data error))
  (let [{:keys [type]} (ex-data error)
        status (get-status-by-type type)]
    (assoc context :response {:status status :body {:message (.getLocalizedMessage error) :type type}})))

(def error-interceptor
  (i/interceptor {:name ::catch-erro
                  :error catch-error}))
