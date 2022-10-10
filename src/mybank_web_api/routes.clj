(ns mybank-web-api.routes
  (:require
   [io.pedestal.http.route :as route]
   [mybank-web-api.controllers.conta :as m.controllers.conta]
   [mybank-web-api.interceptors.conta :as m.interceptors.conta]
   [mybank-web-api.interceptors.error :as m.interceptors.error]
   [mybank-web-api.interceptors.header :as m.interceptors.header]))

(def routes
  (route/expand-routes
   #{["/saldo/:id"
      :get
      [m.interceptors.conta/contas-interceptor
       m.interceptors.header/content-type-header-interceptor
       m.controllers.conta/get-saldo]
      :route-name ::saldo]
     ["/deposito/:id"
      :post
      [m.interceptors.conta/contas-interceptor
       m.interceptors.error/error-interceptor
       m.interceptors.header/content-type-header-interceptor
       m.controllers.conta/make-deposit]
      :route-name ::deposito]}))
