(ns db.core
  (:require [[yesql.core :refer [defqueries]]
             [clojure.java.jdbc :as db :refer :all]]))

(def db-spec {:subprotocol "mysql"
              :subname "//127.0.0.1:3306/chatdb"
              :user "root"
              :password "shoot"})

(db/get-connection)

