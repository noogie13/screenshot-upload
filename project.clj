(defproject screenshot-upload "0.1.0"
  :description "Example for my blog."
  :url "https://solb.io"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [http-kit "2.2.0"]
                 [ring/ring-defaults "0.3.2"]]
  :main screenshot-upload.handler
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler screenshot-upload.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
