#!/usr/bin/env node

var mongoose = require('mongoose')
mongoose.Promise = require('bluebird')

//Load databaseString to connect to from .env
require('dotenv').config();

mongoose.connect(process.env.databaseString, {useNewUrlParser: true, useUnifiedTopology: true});
mongoose.connection
  .on('error', console.error.bind(console, 'connection error:'))
  .once('open', function () {
    //Drop everything
    mongoose.connection.db.dropDatabase((err, result)=>{
        mongoose.connection.close();
    })
  })
