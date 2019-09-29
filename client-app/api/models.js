const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const Hackathons = new Schema({
  title: String,
  date_start: Date,
  date_end: Date,
  text: String,
  cover: String,
  city: String,
  country: String,
  prize: [String],
  currency: String,
  overnight: String,
  challenges: String,
  tags: [String],
  urls: [String],
  pics: [String],
  position: Number,
  cover: String
})
Hackathons.index({title: 1, date_start: 1}, {unique: true});

const model = mongoose.model('hackathons', Hackathons);

module.exports = model;