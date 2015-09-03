(ns db.core)

(require '[clojure.java.jdbc :as db])
(require '[pandect.algo.sha1 :refer :all]
         '[clojure.java.io :as io])

(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/chatdb"
               :user "root"
               :password "shoot"})

(defn register-db [nick password]
  (println "register-db was called")
  (db/insert! mysql-db :User
              {:nick nick
               :password password
               :sessionId (sha1 (str nick password))}))

(defn login-db [nick password]
  (println "login-db was called")
  (first (db/query mysql-db ["select *from User where nick = ? and password = ?" nick password] :id :lastLogin)))


