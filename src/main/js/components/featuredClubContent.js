import React from "react";
import "./featuredPage.css";
import {useNavigate} from "react-router-dom";

function FeaturedClubContent({ featuredClubs, setFeaturedClubs, clubIndex}) {

  let navigate = useNavigate();

  let imageSource = featuredClubs[clubIndex].mediaURL
  if(imageSource != null) {
    imageSource = imageSource.trim()
  }


  return (
    <div>
      <h2 onClick={(featuredClubs[clubIndex].clubId != null) ? () => navigate("/club-information/"+featuredClubs[clubIndex].clubId) : null}>{featuredClubs[clubIndex].clubName}</h2>
      <div className="featured-club-inner-img-div">
        <img className="featured-club-img" src={(imageSource == null || imageSource.length == 0) ? require("../img/ccalogo.png") : imageSource}></img>
      </div>
      <div className="featured-club-inner-description-div">
        <p>{featuredClubs[clubIndex].description}</p>
      </div>
    </div>
  );
}

export default FeaturedClubContent;