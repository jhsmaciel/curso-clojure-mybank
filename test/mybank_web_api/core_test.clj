(ns mybank-web-api.core-test
  (:require [clojure.test :refer :all]
            [mybank-web-api.core :as m.core]
            [test.mybank-web-api.utils :as m.utils]))

(def saldo-1-response
  {:status 200,
   :body "{:saldo 100}",
   :headers
   {"Strict-Transport-Security" "max-age=31536000; includeSubdomains",
    "X-Frame-Options" "DENY",
    "X-Content-Type-Options" "nosniff",
    "X-XSS-Protection" "1; mode=block",
    "X-Download-Options" "noopen",
    "X-Permitted-Cross-Domain-Policies" "none",
    "Content-Security-Policy"
    "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;",
    "Content-Type" "text/plain"}})

(deftest api-test
  (let [_ (m.core/reset-server)]
    (testing "Verificar saldo"
      (is (= saldo-1-response (m.utils/test-request m.core/server :get "/saldo/1"))))))
