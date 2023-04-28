db.createUser(
    {
      user: "user1",
      pwd: "mypassword",
      roles: [{role: "readWrite", db: "catalog"}]
    }
);
