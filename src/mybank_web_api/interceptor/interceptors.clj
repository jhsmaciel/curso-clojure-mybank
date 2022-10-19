(ns mybank-web-api.interceptor.interceptors
  (:require
   [com.stuartsierra.component :as component]
   [mybank-web-api.interceptor.error :as m.interceptor.error]
   [mybank-web-api.interceptor.debug :as m.interceptor.debug]
   [mybank-web-api.interceptor.header :as m.interceptor.header]))

(defrecord Interceptors []
  component/Lifecycle

  (start [this]
    (assoc this
           :interceptors
           [m.interceptor.error/error-interceptor
            m.interceptor.header/coerce-body-interceptor
            m.interceptor.header/supported-types-interceptor
            m.interceptor.debug/debug-print-interceptor]))

  (stop [this]
    (assoc this :interceptors nil)))

(defn new-interceptors []
  (->Interceptors))
