function foo() {

    for each (var i in [1, 2, 3, 4, 5]) {
        print("line " + i);
        print("next");
    }
}

foo();
print("Hello from Nashorn!");
