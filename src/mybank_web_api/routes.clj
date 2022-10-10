(ns mybank-web-api.routes
  (:require
   [io.pedestal.http.route :as route]
   [mybank-web-api.controllers.conta :as m.controllers.conta]
   [mybank-web-api.interceptors.conta :as m.interceptors.conta]
   [mybank-web-api.interceptors.error :as m.interceptors.error]
   [mybank-web-api.interceptors.header :as m.interceptors.header]))

(defn fazer-deposito
  [request]
  (m.controllers.conta/deposito! request)
  {:status 200
   :body (m.controllers.conta/get-saldo request)})

(defn fazer-saque
  [request]
  (m.controllers.conta/saque! request)
  {:status 200
   :body (m.controllers.conta/get-saldo request)})

(defn buscar-saldo
  [request]
  {:status 200
   :body (m.controllers.conta/get-saldo request)})


(def routes
  (route/expand-routes
   #{["/saldo/:id"
      :get
      [m.interceptors.conta/contas-interceptor
       m.interceptors.error/error-interceptor
       m.interceptors.header/content-type-header-interceptor
       buscar-saldo]
      :route-name ::saldo]
     ["/deposito/:id"
      :post
      [m.interceptors.conta/contas-interceptor
       m.interceptors.error/error-interceptor
       m.interceptors.header/content-type-header-interceptor
       fazer-deposito]
      :route-name ::deposito]
     ["/saque/:id"
      :post
      [m.interceptors.conta/contas-interceptor
       m.interceptors.error/error-interceptor
       m.interceptors.header/content-type-header-interceptor
       fazer-saque]
      :route-name ::saque]}))
