package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class AutoGrader {

	// Test if the code uses class creation, object creation, and method invocations
	// correctly
	public boolean testClassAndObjectCreation(String filePath) throws IOException {
		System.out.println("Starting testClassAndObjectCreation with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		boolean hasClassAndObjectOperations = false;

		// Check for class creation (Car class in this case)
		System.out.println("------ Class Creation ------");
		boolean classFound = false;
		for (TypeDeclaration<?> typeDecl : cu.findAll(TypeDeclaration.class)) {
			if (typeDecl.getNameAsString().equals("Car")) {
				System.out.println("Class or Interface 'Car' found: " + typeDecl.getName());
				hasClassAndObjectOperations = true;
				classFound = true;
				break;
			}
		}
		if (!classFound) {
			System.out.println("Error: Class 'Car' not found in the code.");
			return false; // Early exit if class creation is missing
		}

		// Check for object creation (using 'new Car(...)')
		System.out.println("------ Object Creation ------");
		boolean objectCreated = false;
		for (ObjectCreationExpr objectCreation : cu.findAll(ObjectCreationExpr.class)) {
			if (objectCreation.getType().getNameAsString().equals("Car")) {
				System.out.println("Object created using 'new Car(...)': " + objectCreation);
				hasClassAndObjectOperations = true;
				objectCreated = true;
				break;
			}
		}
		if (!objectCreated) {
			System.out.println("Error: Object creation for 'Car' is missing.");
			return false; // Early exit if object creation is missing
		}

		// Check for method invocations (displayDetails method)
		System.out.println("------ Method Invocation ------");
		boolean methodCalled = false;
		for (ExpressionStmt stmt : cu.findAll(ExpressionStmt.class)) {
			if (stmt.getExpression() instanceof MethodCallExpr) {
				MethodCallExpr methodCall = (MethodCallExpr) stmt.getExpression();
				if (methodCall.getNameAsString().equals("displayDetails")) {
					System.out.println("Method 'displayDetails' called: " + methodCall);
					hasClassAndObjectOperations = true;
					methodCalled = true;
					break;
				}
			}
		}
		if (!methodCalled) {
			System.out.println("Error: 'displayDetails' method is not called.");
			return false; // Early exit if method invocation is missing
		}

		// If all operations were found, return true
		System.out.println("Test passed: All required class, object, and method operations were found.");
		return true;
	}
}
