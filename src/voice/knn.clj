(ns voice.knn
  (:use clojure.core.matrix))

(set-current-implementation :vectorz)

(defn slurp-bytes
  "Slurp the bytes from a slurpable thing"
  [x]
  (with-open [out (java.io.ByteArrayOutputStream.)]
    (clojure.java.io/copy (clojure.java.io/input-stream x) out)
    (.toByteArray out)))

;(def cfar-train-bin (slurp-bytes "resources/train.bin"))
(def cifar-dir "resources/cifar-10-batches-bin")

(defn split-cifar-pixel-data [data]
  (reshape (array (map #(bit-and % 0xFF) data)) [3 32 32]))

(defn list-of-maps->map-of-vectors [list-of-maps]
  (println "infinite loop in list of maps->map of vectors")
  (let [x (atom 0)]
    (reduce
     (fn [m [k v]]
       ;(println x)
       ;(swap! x (constantly (+ @x 1)))
       (assoc m k (conj (get m k []) v)))
     {}
     (apply concat list-of-maps))))

(defn load-cifar-batch [bin-file]
   (map
     (fn [row]
       {:label (first row)
        :data
        (split-cifar-pixel-data (rest row))})
     (partition 3073 (slurp-bytes bin-file))))

(defn load-all-cifar [dir]
  {:train
   (list-of-maps->map-of-vectors
    (flatten
     (map
      #(doall (load-cifar-batch (str dir "/data_batch_" (+ % 1) ".bin")))
      (range 5))))
   :test
   (load-cifar-batch (str dir "/test_batch.bin"))})

(def cifar (load-all-cifar cifar-dir))












