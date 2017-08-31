(ns adventure-bot.db
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [korma.db :refer :all]
            [korma.core :refer :all]))

(def spec (sqlite3 {:db         (last (string/split
                                        (str (io/resource "sqlite.db"))
                                        #"file:"))
                    :make-pool? true}))

(defdb connection spec)

(declare user)

(defentity user
  (pk :id)
  (table :users)
  (entity-fields :username))

(defn init []
  (default-connection connection)
  (select user
    (fields :id :username)))



