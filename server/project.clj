(defproject server "0.1.0-SNAPSHOT"
  :description "Chat server"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :uberjar-name "server.jar"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.19"]
                 [hiccup "1.0.5"]
                 [metosin/compojure-api "0.22.2"]
                 [cheshire "5.5.0"]
                 [lib-noir "0.9.9"]
                 [buddy "0.6.1"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [mysql/mysql-connector-java "5.1.36"]
                 [yesql "0.5.0"]
                 [environ "1.0.0"]
                 [clj-time "0.11.0"]
                 [com.draines/postal "1.11.3"]]
  :main server.handler
  :plugins      [[lein-environ "1.0.0"]]
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
