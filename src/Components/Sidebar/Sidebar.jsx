import React, { useState } from "react";
import './sidebar.css'

const Sidebar = ({ onFilter }) => {
  const [filters, setFilters] = useState({
    category: "",
    priceRange: "",
    availability: "",
    rating: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const applyFilters = () => {
    if (onFilter) onFilter(filters);
  };

  return  (
    <aside className="ayurveda-sidebar">
      {/* <div className="ayurveda-card">
        <article className="filter-group">
         <header className="ayurveda-card-header">
            <a href="#" data-toggle="collapse" data-target="#collapse_1" aria-expanded="true">
              <i className="icon-control fa fa-chevron-down"></i>
              <h6 className="ayurveda-filter-title">Product Type</h6>
            </a>
          </header>
          <div className="filter-content collapse show" id="collapse_1">
            <div className="ayurveda-card-body">
              <form className="ayurveda-search-box">
                <div className="input-group">
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Search Product Type"
                  />
                  <div className="input-group-append">
                    <button className="btn ayurveda-search-button" type="button">
                      <i className="fa fa-search"></i>
                    </button>
                  </div>
                </div>
              </form>

              <ul className="ayurveda-list-menu">
                <li><a href="#">Herbal Supplements</a></li>
                <li><a href="#">Ayurvedic Oils</a></li>
                <li><a href="#">Herbal Teas</a></li>
                <li><a href="#">Organic Skincare</a></li>
                <li><a href="#">Medicinal Plants</a></li>
                <li><a href="#">Essential Oils</a></li>
              </ul>
            </div>
          </div>
        </article>
      </div>

      <div className="ayurveda-card">
        <article className="filter-group">
          <header className="ayurveda-card-header">
            <a href="#" data-toggle="collapse" data-target="#collapse_2" aria-expanded="true">
              <i className="icon-control fa fa-chevron-down"></i>
              <h6 className="ayurveda-filter-title">Brands</h6>
            </a>
          </header>
          <div className="filter-content collapse show" id="collapse_2">
            <div className="ayurveda-card-body">
              <label className="custom-control custom-checkbox">
                <input type="checkbox" checked className="custom-control-input" />
                <div className="custom-control-label">Brand A
                  <b className="badge badge-pill badge-light float-right">120</b>
                </div>
              </label>
              <label className="custom-control custom-checkbox">
                <input type="checkbox" className="custom-control-input" />
                <div className="custom-control-label">Brand B
                  <b className="badge badge-pill badge-light float-right">15</b>
                </div>
              </label>
              <label className="custom-control custom-checkbox">
                <input type="checkbox" className="custom-control-input" />
                <div className="custom-control-label">Brand C
                  <b className="badge badge-pill badge-light float-right">35</b>
                </div>
              </label>
            </div>
          </div>
        </article>
      </div>

      <div className="ayurveda-card">
        <article className="filter-group">
          <header className="ayurveda-card-header">
            <a href="#" data-toggle="collapse" data-target="#collapse_3" aria-expanded="true">
              <i className="icon-control fa fa-chevron-down"></i>
              <h6 className="ayurveda-filter-title">Price Range</h6>
            </a>
          </header>
          <div className="filter-content collapse show" id="collapse_3">
            <div className="ayurveda-card-body">
              <input type="range" className="custom-range" min="0" max="1000" />
              <div className="form-row">
                <div className="form-group col-md-6">
                  <label>Min</label>
                  <input className="form-control" placeholder="$0" type="number" />
                </div>
                <div className="form-group text-right col-md-6">
                  <label>Max</label>
                  <input className="form-control" placeholder="$1000" type="number" />
                </div>
              </div>
              <button className="btn ayurveda-apply-button">Apply</button>
            </div>
          </div>
        </article>
      </div>

      <div className="ayurveda-card">
        <article className="filter-group">
          <header className="ayurveda-card-header">
            <a href="#" data-toggle="collapse" data-target="#collapse_4" aria-expanded="true">
              <i className="icon-control fa fa-chevron-down"></i>
              <h6 className="ayurveda-filter-title">Sizes</h6>
            </a>
          </header>
          <div className="filter-content collapse show" id="collapse_4">
            <div className="ayurveda-card-body">
              <label className="checkbox-btn">
                <input type="checkbox" />
                <span className="btn btn-light">XS</span>
              </label>

              <label className="checkbox-btn">
                <input type="checkbox" />
                <span className="btn btn-light">SM</span>
              </label>

              <label className="checkbox-btn">
                <input type="checkbox" />
                <span className="btn btn-light">LG</span>
              </label>

              <label className="checkbox-btn">
                <input type="checkbox" />
                <span className="btn btn-light">XXL</span>
              </label>
            </div>
          </div>
        </article>
      </div>

      <div className="ayurveda-card">
        <article className="filter-group">
          <header className="ayurveda-card-header">
            <a href="#" data-toggle="collapse" data-target="#collapse_5" aria-expanded="false">
              <i className="icon-control fa fa-chevron-down"></i>
              <h6 className="ayurveda-filter-title">More Filters</h6>
            </a>
          </header>
          <div className="filter-content collapse show" id="collapse_5">
            <div className="ayurveda-card-body">
              <label className="custom-control custom-radio">
                <input type="radio" name="myfilter_radio" checked className="custom-control-input" />
                <div className="custom-control-label">Any Condition</div>
              </label>

              <label className="custom-control custom-radio">
                <input type="radio" name="myfilter_radio" className="custom-control-input" />
                <div className="custom-control-label">Brand New</div>
              </label>

              <label className="custom-control custom-radio">
                <input type="radio" name="myfilter_radio" className="custom-control-input" />
                <div className="custom-control-label">Used Items</div>
              </label>

              <label className="custom-control custom-radio">
                <input type="radio" name="myfilter_radio" className="custom-control-input" />
                <div className="custom-control-label">Very Old</div>
              </label>
            </div>
          </div> 
        </article>
      </div> */}
    </aside>
  );
};

export default Sidebar;
