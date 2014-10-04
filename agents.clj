;;  Agent that allows for asynchronous and independent changes to shared mutable data.
;; They are useful to run a task (function) on a different thread, with the return value of the function becoming the new value of the agent.
(def total-cpu-time (agent 0))

(deref total-cpu-time)
@total-cpu-time


;;send
(send the-agent the-function & more-args)

; When it runs, the first parameter it’s supplied is the current value of the agent, and the remaining parameters are the ones passed via send.
;; Actions sent to agents using send are executed on a fixed thread pool maintained by Clojure.
;;If you send lots of actions to agents (more than the number of free threads in this pool), they get queued and will run in the order in which they were sent.
;; Only one action runs on a particular agent at a time. This thread pool doesn’t grow in size, no matter how many actions are queued up.
;; This is why you should use send for actions that are CPU intensive and don’t block, because blocking actions will use up the thread pool.
(send total-cpu-time + 700)
@total-cpu-time ;;700


;; send-off can handle potential blocking actions. This is because it uses a different thread pool from the one used by send, and this thread pool can grow in size to accommodate more actions sent using send-off.
;; Again, only one action runs on a particular agent at a time.
(send-off the-agent the-function & more-args)


;; await is a function that’s useful when execution must stop and wait for actions that were previously dispatched to certain agents to be completed.
(await & the-agents)

;;await blocks indefinitely, so if any of the actions didn’t return successfully, the current thread wouldn’t be able to proceed.
;; In order to avoid this, Clojure also provides the await-for function. The general form looks similar to that of await, but it accepts a maximum timeout in milliseconds:
(await-for timeout-in-millis & the-agents)

;; Agent Errors

;; When an action doesn’t complete successfully (it throws an exception), the agent knows about it.
;; If you try to dereference an agent that’s in such an error state, Clojure will throw another exception.
(def bad-agent (agent 10))
(send bad-agent / 0)

(deref bad-agent) ;; Agent has errors  [Thrown class java.lang.Exception] Agent has errors

;; You can discern the error is by using the agent-errors function:
(agent-errors bad-agent) ;; (#<ArithmeticException java.lang.ArithmeticException: Divide by zero>)

;; If an agent has errors, you can’t send it any more actions. If you do, Clojure throws the same exception, informing you that the agent has errors.
;; In order to make the agent usable again, Clojure provides the clear-agent-errors function
(clear-agent-errors bad-agent)


;; Validations

;;The complete general form of the agent function that creates new agents is
(agent initial-state & options)

;; The options allowed are :meta metadata-map :validator validator-fn
;; If the :validator option is used, it should be accompanied by either nil or a function that accepts one argument.
;; The validator-fn is passed the intended new state of the agent, and it can apply any business rules in order to allow or disallow the change to occur.
;; If the validator function returns false or throws an exception, then the state of the agent is not mutated.
