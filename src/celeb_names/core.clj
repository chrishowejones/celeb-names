(ns celeb-names.core
  (:gen-class)
  (:require [clojure.string :as s]))


(def vowels #{\a \e \i \o \u})

(defn vowel? [n]
  (some vowels [n]))

(defn take-upto-vowel [name]
  (take-while #(not (vowel? %)) name))

(defn drop-upto-vowel [name]
  (drop-while #(not (vowel? %)) name))

(defn consonant-after-vowel
  [[vowel-found? stems-seq] x]
  (if (vowel? x)
    [true (conj stems-seq x)]
    (if vowel-found?
      (reduced [true (conj stems-seq x)])
      [false (conj stems-seq x)])))

(defn take-upto-consonant-after-vowel
  [name]
  (->
   (reduce consonant-after-vowel [false []] name)
   second
   s/join))

(defn generate-celeb-name
  [beginning end]
  (s/join (concat beginning end)))

(defn prompt-for-name
  [prompt]
  (println prompt)
  (flush)
  (s/lower-case (read-line)))

(defn conjoin-celeb-names
  []
  (let [name1 (prompt-for-name "Enter first celeb name")
        name2 (prompt-for-name "Enter second celeb name")]
    (if (= (first name1) (first name2))
      (println (generate-celeb-name (take-upto-consonant-after-vowel name1) (drop-upto-vowel name2)))
     (println (generate-celeb-name (take-upto-vowel name1) (drop-upto-vowel name2))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (conjoin-celeb-names))


(comment

  (clojure.string/join (concat (take-upto-vowel "brad")
                               (drop-upto-vowel "angelina")))



  )
