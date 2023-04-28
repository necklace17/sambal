db.createUser(
    {
      user: "catalogdbuser",
      pwd: "mypassword",
      roles: [{role: "readWrite", db: "cataloginfo"}]
    }
);
