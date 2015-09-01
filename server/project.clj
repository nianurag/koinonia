(defproject server "0.1.0-SNAPSHOT"
  :description "Chat server"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [hiccup "1.0.5"]
                 [metosin/compojure-api "0.22.2"]
                 [lib-noir "0.9.9"]]
  :main server.handler
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
