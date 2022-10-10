(ns mybank-web-api.controllers.conta
  (:require [mybank-web-api.logics.conta :as m.logics.conta]))

(defn get-saldo [request]
  (let [contas (-> request :contas)
        id-conta (-> request :path-params :id keyword)]
    (m.logics.conta/get-conta @contas id-conta)))

(defn deposito! [request]
  (let [contas (-> request :contas)
        id-conta (-> request :path-params :id keyword)
        valor-deposito (-> request :body slurp parse-double)]
    (swap! contas m.logics.conta/atualiza-saldo-conta id-conta valor-deposito +)))

(defn saque! [request]
  (let [contas (-> request :contas)
        id-conta (-> request :path-params :id keyword)
        valor-saque (-> request :body slurp parse-double)]
    (swap! contas m.logics.conta/atualiza-saldo-conta id-conta valor-saque -)))
