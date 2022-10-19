(ns mybank-web-api.interceptor.debug
  (:require
   [io.pedestal.interceptor :as i]))

(defonce debug (atom false))

(defn toggle-debug!
  []
  (swap! debug #(not %)))

(defn print-when-debug
  "Log args when debug is true"
  [& args]
  (when @debug
    (apply println "[LOG] " args)))

(defn print-debug
  "Logs the context and return the same."
  [context]
  (print-when-debug context)
  context)

(def debug-print-interceptor
  (i/interceptor {:name :print-n-continue
                  :enter print-debug
                  :leave print-debug}))
