(defproject mybank-web-api "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.route "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [org.slf4j/slf4j-simple "1.7.28"]
                 [clj-http "3.12.1"]
                 [org.clojure/data.json "2.4.0"]
                 [prismatic/schema "1.4.1"]
                 [com.stuartsierra/component "1.0.0"]
                 [walmartlabs/system-viz "0.4.0"]]
  :main ^:skip-aot mybank-web-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
