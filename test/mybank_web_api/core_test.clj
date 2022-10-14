(ns mybank-web-api.core-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [mybank-web-api.core :as m.core]
   [mybank-web-api.server :refer [server reset-server]]
   [mybank-web-api.utils :as m.utils]
   [mybank-web-api.db :as m.db]))

(def balance-account-1-payload
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

(def account-not-found-payload
  {:status 400,
   :body "{:message \"clojure.lang.ExceptionInfo in Interceptor  - Account not found.\", :type :mybank-web-api.logic.account/account-not-found}",
   :headers
   {"Strict-Transport-Security" "max-age=31536000; includeSubdomains",
    "X-Frame-Options" "DENY",
    "X-Content-Type-Options" "nosniff",
    "X-XSS-Protection" "1; mode=block",
    "X-Download-Options" "noopen",
    "X-Permitted-Cross-Domain-Policies" "none",
    "Content-Security-Policy"
    "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;",
    "Content-Type" "application/edn"}}) 

(def account-without-balance-payload
  {:status 412,
   :body "{:message \"clojure.lang.ExceptionInfo in Interceptor  - Account don't has balance.\", :type :mybank-web-api.logic.account/account-without-balance}",
   :headers
   {"Strict-Transport-Security" "max-age=31536000; includeSubdomains",
    "X-Frame-Options" "DENY",
    "X-Content-Type-Options" "nosniff",
    "X-XSS-Protection" "1; mode=block",
    "X-Download-Options" "noopen",
    "X-Permitted-Cross-Domain-Policies" "none",
    "Content-Security-Policy"
    "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;",
    "Content-Type" "application/edn"}})

(deftest api-test
  (let [_ (reset-server)
        __ (m.db/reset-db)]
    (testing "Verify balances"
      (is (= balance-account-1-payload (m.utils/test-request server :get "/saldo/1")))
      (is (= account-not-found-payload (m.utils/test-request server :get "/saldo/6"))))
    (testing "Make deposits"
      (is (= (assoc balance-account-1-payload :body "{:saldo 300.0}")
             (m.utils/test-post server :post "/deposito/1" "200")))
      (is (= account-not-found-payload (m.utils/test-post server :post "/deposito/5" "200"))))
    (testing "Make withdraw"
      (is (= (assoc balance-account-1-payload :body "{:saldo 200.0}")
             (m.utils/test-post server :post "/saque/1" "100")))
      (is (= account-not-found-payload
             (m.utils/test-post server :post "/saque/5" "200"))))
    (testing "Make withdraw without balance"
      (is (= account-without-balance-payload
             (m.utils/test-post server :post "/saque/1" "200.1"))))))
