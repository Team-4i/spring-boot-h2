/**
 * Spring Boot Template - Main JavaScript
 */
document.addEventListener('DOMContentLoaded', function() {
    // Remove any loading indicators immediately when the page is loaded
    const loadingIndicator = document.getElementById('loading-indicator');
    if (loadingIndicator) {
        loadingIndicator.style.display = 'none';
    }

    // Add animation to feature cards
    const featureCards = document.querySelectorAll('.feature-card');
    if (featureCards.length > 0) {
        featureCards.forEach((card, index) => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(20px)';
            setTimeout(() => {
                card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
                card.style.opacity = '1';
                card.style.transform = 'translateY(0)';
            }, 100 * index);
        });
    }

    // Add animation to item cards
    const itemCards = document.querySelectorAll('.item-card');
    if (itemCards.length > 0) {
        itemCards.forEach((card, index) => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(20px)';
            setTimeout(() => {
                card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
                card.style.opacity = '1';
                card.style.transform = 'translateY(0)';
            }, 100 * index);
        });
    }

    // Form validation
    const forms = document.querySelectorAll('form');
    if (forms.length > 0) {
        forms.forEach(form => {
            form.addEventListener('submit', function(event) {
                let isValid = true;
                
                // Validate required fields
                const requiredFields = form.querySelectorAll('[required]');
                requiredFields.forEach(field => {
                    if (!field.value.trim()) {
                        isValid = false;
                        field.classList.add('error');
                        
                        // Add error message if it doesn't exist
                        let errorMsg = field.parentNode.querySelector('.error-message');
                        if (!errorMsg) {
                            errorMsg = document.createElement('div');
                            errorMsg.className = 'error-message';
                            errorMsg.textContent = 'This field is required';
                            field.parentNode.appendChild(errorMsg);
                        }
                    } else {
                        field.classList.remove('error');
                        const errorMsg = field.parentNode.querySelector('.error-message');
                        if (errorMsg) {
                            errorMsg.remove();
                        }
                    }
                });
                
                if (!isValid) {
                    event.preventDefault();
                }
            });
        });
    }

    // Apply a gentle fade-in effect to sections but don't fade out on navigation
    const sections = document.querySelectorAll('section');
    if (sections.length > 0) {
        // Set sections to visible by default to avoid the fade-out effect on navigation
        sections.forEach(section => {
            section.style.opacity = '1';
            section.style.transition = 'opacity 0.5s ease-out';
        });
        
        // Add a class to track if animation has already been applied
        // This ensures animations only run on initial page load
        if (!document.body.classList.contains('animations-applied')) {
            sections.forEach(section => {
                section.style.opacity = '0';
                setTimeout(() => {
                    section.style.opacity = '1';
                }, 100);
            });
            
            document.body.classList.add('animations-applied');
        }
        
        // Remove the scroll event listener that was causing issues
        // by constantly modifying opacity during scrolling
    }

    // Apply active class to current navigation link
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('nav ul li a');
    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPath) {
            link.classList.add('active');
        } else if (currentPath.includes('/items') && href === '/items') {
            link.classList.add('active');
        }
    });
    
    // Add a listener for navigation links to show loading indicator
    document.querySelectorAll('a').forEach(link => {
        // Exclude links that open in new tabs or are anchor links
        if (!link.getAttribute('target') && !link.getAttribute('href').startsWith('#')) {
            link.addEventListener('click', function(e) {
                // Don't show loading for H2 console links
                if (link.getAttribute('href') !== '/h2-console') {
                    // Create or show loading indicator
                    showLoadingIndicator();
                    
                    // Store a flag in session storage to indicate navigation is happening
                    sessionStorage.setItem('navigating', 'true');
                }
            });
        }
    });
    
    // Check if we just navigated from another page
    if (sessionStorage.getItem('navigating') === 'true') {
        // Make sure all sections are visible immediately
        sections.forEach(section => {
            section.style.opacity = '1';
        });
        sessionStorage.removeItem('navigating');
    }
});

// Function to create and show a loading indicator
function showLoadingIndicator() {
    let loadingIndicator = document.getElementById('loading-indicator');
    
    // Create the loading indicator if it doesn't exist
    if (!loadingIndicator) {
        loadingIndicator = document.createElement('div');
        loadingIndicator.id = 'loading-indicator';
        loadingIndicator.innerHTML = '<div class="spinner"></div>';
        loadingIndicator.style.position = 'fixed';
        loadingIndicator.style.top = '0';
        loadingIndicator.style.left = '0';
        loadingIndicator.style.width = '100%';
        loadingIndicator.style.height = '100%';
        loadingIndicator.style.backgroundColor = 'rgba(255, 255, 255, 0.7)';
        loadingIndicator.style.display = 'flex';
        loadingIndicator.style.justifyContent = 'center';
        loadingIndicator.style.alignItems = 'center';
        loadingIndicator.style.zIndex = '9999';
        
        const spinner = loadingIndicator.querySelector('.spinner');
        spinner.style.width = '40px';
        spinner.style.height = '40px';
        spinner.style.border = '4px solid rgba(66, 133, 244, 0.3)';
        spinner.style.borderTop = '4px solid var(--primary-color, #4285f4)';
        spinner.style.borderRadius = '50%';
        spinner.style.animation = 'spin 1s linear infinite';
        
        // Add the keyframe animation
        const style = document.createElement('style');
        style.innerHTML = `
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        `;
        document.head.appendChild(style);
        
        document.body.appendChild(loadingIndicator);
    } else {
        loadingIndicator.style.display = 'flex';
    }
} 