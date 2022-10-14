(ns mybank-web-api.routes
  (:require
   [io.pedestal.http.route :as route]
   [mybank-web-api.controller.account :as m.controller.account]
   [mybank-web-api.interceptor.account :as m.interceptor.account]
   [mybank-web-api.interceptor.error :as m.interceptor.error]
   [mybank-web-api.interceptor.header :as m.interceptor.header]))

(defn make-deposit
  [request]
  (m.controller.account/deposit! request)
  {:status 200
   :body (m.controller.account/get-balance request)})

(defn make-withdraw
  [request]
  (m.controller.account/withdraw! request)
  {:status 200
   :body (m.controller.account/get-balance request)})

(defn get-balance
  [request]
  {:status 200
   :body (m.controller.account/get-balance request)})


(def routes
  (route/expand-routes
   #{["/saldo/:id"
      :get
      [m.interceptor.account/accounts-interceptor
       m.interceptor.error/error-interceptor
       m.interceptor.header/content-type-header-interceptor
       get-balance]
      :route-name ::balance]
     ["/deposito/:id"
      :post
      [m.interceptor.account/accounts-interceptor
       m.interceptor.error/error-interceptor
       m.interceptor.header/content-type-header-interceptor
       make-deposit]
      :route-name ::deposit]
     ["/saque/:id"
      :post
      [m.interceptor.account/accounts-interceptor
       m.interceptor.error/error-interceptor
       m.interceptor.header/content-type-header-interceptor
       make-withdraw]
      :route-name ::withdraw]}))
