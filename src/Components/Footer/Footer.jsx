import React from 'react';
import './Footer.css';

function Footer() {
  return (
    <div className='footerContain'>
      <div className="conainerFooter">
        <div className="firstConatiner">
          {/* Additional content for the first container if needed */}
        </div>
        <div className="secondContainer">
          <div className="footer-columns">
            {/* Company Column */}
            <div className="footer-column">
              <h5>Company</h5>
              <ul>
                <li><a href="/about-us" aria-label="About Us">About Us</a></li>
                <li><a href="/careers" aria-label="Careers">Careers</a></li>
                <li><a href="/partners" aria-label="Our Partners">Our Partners</a></li>
                <li><a href="/press" aria-label="Press Releases">Press Releases</a></li>
              </ul>
            </div>
            
            {/* Help & FAQ Column */}
            <div className="footer-column">
              <h5>Help & FAQ</h5>
              <ul>
                <li><a href="/contact-us" aria-label="Contact Us">Contact Us</a></li>
                <li><a href="/shipping" aria-label="Shipping Policy">Shipping Policy</a></li>
                <li><a href="/returns" aria-label="Returns Policy">Returns Policy</a></li>
                <li><a href="/faq" aria-label="FAQ">FAQ</a></li>
              </ul>
            </div>
            
            {/* Resources Column */}
            <div className="footer-column">
              <h5>Resources</h5>
              <ul>
                <li><a href="/blog" aria-label="Our Blog">Our Blog</a></li>
                <li><a href="/ayurveda-tips" aria-label="Ayurveda Tips">Ayurveda Tips</a></li>
                <li><a href="/ebooks" aria-label="Free eBooks">Free eBooks</a></li>
                <li><a href="/community" aria-label="Community Forum">Community Forum</a></li>
              </ul>
            </div>
            
            {/* Social Media Column */}
            <div className="footer-column">
              <h5>Connect with Us</h5>
              <div className="social-media">
                <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" aria-label="Twitter">
                  <i className="bi bi-twitter"></i>
                </a>
                <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" aria-label="Instagram">
                  <i className="bi bi-instagram"></i>
                </a>
                <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" aria-label="Facebook">
                  <i className="bi bi-facebook"></i>
                </a>
                <a href="https://github.com" target="_blank" rel="noopener noreferrer" aria-label="GitHub">
                  <i className="bi bi-github"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Footer;
