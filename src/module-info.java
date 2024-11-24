/**
 * Module configuration for Assignment2 This module requires JUnit 4
 */
module Assignment2 {
	requires junit;

	// Open your test package for JUnit reflection
	opens unitTests;

	// Open implementation package
	opens implementations;

	// Export your packages
	exports implementations;
	exports utilities;
}