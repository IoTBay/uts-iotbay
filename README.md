# uts-iotbay

## Development Setup
### FAQ
#### Tomcat can't start because port 8080 is already in use?
* First, see what process ID is using the port: Open CMD and run as Administrator: `netstat -ano | find "LISTEN" | find ":8080"`
```
 TCP    [::]:8080              [::]:0                 LISTENING       4
```
* If the last digit is NOT 4 then open task manager, look in the PID column to find what app is taking that process.
* If the process IS 4 then this is because of HTTP Sys port sharing. We can use "netsh" to find the HTTP listener using this port.
* Again in CMD run this command: `netsh http show servicestate`
```
netsh http>show servicestate

Snapshot of HTTP service state (Server Session View):
-----------------------------------------------------

Server session ID: FF00000420000001
    Version: 2.0
    State: Active
    Properties:
        Max bandwidth: 4294967295
        Timeouts:
            Entity body timeout (secs): 120
            Drain entity body timeout (secs): 120
            Request queue timeout (secs): 120
            Idle connection timeout (secs): 120
            Header wait timeout (secs): 120
            Minimum send rate (bytes/sec): 150
    URL groups:
    URL group ID: FE00000440000001
        State: Active
        Request queue name: Request queue is unnamed.
        Properties:
            Max bandwidth: inherited
            Max connections: inherited
            Timeouts:
                Timeout values inherited
            Number of registered URLs: 1
            Registered URLs:
                HTTP://*:5357/4C2CDC17-BEFE-4441-899A-CC530F03F2DF/

Server session ID: FF00000620000011
    Version: 2.0
    State: Active
    Properties:
        Max bandwidth: 4294967295
        Timeouts:
            Entity body timeout (secs): 120
            Drain entity body timeout (secs): 120
            Request queue timeout (secs): 120
            Idle connection timeout (secs): 120
            Header wait timeout (secs): 120
            Minimum send rate (bytes/sec): 150
    URL groups:
    URL group ID: FD00000440001314
        State: Active
        Request queue name: Request queue is unnamed.
        Properties:
            Max bandwidth: inherited
            Max connections: inherited
            Timeouts:
                Timeout values inherited
            Number of registered URLs: 1
            Registered URLs:
                HTTP://LOCALHOST:8080/ **<-- See here that localhost:8080 is registered!**

Request queues:
    Request queue name: Request queue is unnamed.
        Version: 2.0
        State: Active
        Request queue 503 verbosity level: Basic
        Max requests: 1000
        Number of active processes attached: 1
        Process IDs:
            5492

    Request queue name: Request queue is unnamed.
        Version: 2.0
        State: Active
        Request queue 503 verbosity level: Basic
        Max requests: 1000
        Number of active processes attached: 1
        Process IDs:
            37584 **Then see this process ID associated with the second registered server session -- this is the process ID!**

netsh http>quit
```
* After this I found Process ID 37584 using task manager and found it was an MYOB API Addon running in the background.
* Use `telnet localhost 8080` to verify nothing is listening now.
```
C:\Windows\system32>telnet localhost 8080
Connecting To localhost...Could not open connection to the host, on port 8080: Connect failed
```
