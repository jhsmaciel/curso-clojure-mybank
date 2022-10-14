(ns mybank-web-api.controller.account
  (:require [mybank-web-api.logic.account :as m.logic.account]))

(defn get-balance [request]
  (let [accounts (-> request :accounts)
        account-id (-> request :path-params :id keyword)]
    (m.logic.account/get-account @accounts account-id)))

(defn deposit! [request]
  (let [accounts (-> request :accounts)
        account-id (-> request :path-params :id keyword)
        deposit-value (-> request :body slurp parse-double)]

    (swap! accounts m.logic.account/update-account-balance account-id deposit-value +)))

(defn withdraw! [request]
  (let [accounts (-> request :accounts)
        account-id (-> request :path-params :id keyword)
        withdraw-value (-> request :body slurp parse-double)]
    (m.logic.account/has-balance? @accounts account-id withdraw-value)
    (swap! accounts m.logic.account/update-account-balance account-id withdraw-value -)))
