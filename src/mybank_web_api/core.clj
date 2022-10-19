(ns mybank-web-api.core
  (:require
   [mybank-web-api.server :as m.server]
   [mybank-web-api.utils :as m.utils]))

(m.server/reset-server)

(comment
  (m.utils/test-request m.server/server :get "/saldo/1")
  (m.utils/test-request m.server/server :get "/saldo/2")
  (m.utils/test-request m.server/server :get "/saldo/3")
  (m.utils/test-request m.server/server :get "/saldo/4")
  (m.utils/test-post m.server/server :post "/deposito/1" "{\"value\": 199.99}")
  (m.utils/test-post m.server/server :post "/deposito/4" "{\"value\": 325.99}")
  (m.utils/test-post m.server/server :post "/saque/1" "{\"value\": 199.93}")
  (m.utils/test-post m.server/server :post "/saque/4" "{\"value\": 2000.99}"))
