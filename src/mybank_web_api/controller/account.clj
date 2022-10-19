(ns mybank-web-api.controller.account
  (:require
   [mybank-web-api.logic.account :as m.logic.account]))

(defn get-balance [request]
  (let [accounts (-> request :accounts)
        account-id (-> request :path-params :id keyword)]
    (m.logic.account/get-account @accounts account-id)))

(defn deposit! [request]
  (let [accounts (-> request :accounts)
        account-id (-> request :path-params :id keyword)
        deposit-value (-> request :json-params :value bigdec)
        _ (println (keys request))]
    (swap! accounts m.logic.account/update-account-balance account-id deposit-value +)))

(defn withdraw! [request]
  (let [accounts (-> request :accounts)
        account-id (-> request :path-params :id keyword)
        withdraw-value (-> request :json-params :value bigdec)]
    (m.logic.account/has-balance? @accounts account-id withdraw-value)
    (swap! accounts m.logic.account/update-account-balance account-id withdraw-value -)))

