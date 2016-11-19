package com.mif.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.mif.data.Regions;
import com.mif.entities.Person;

@Import(stylesheet = { "context:jumpstart/css/plain.css", "context:jumpstart/css/modal.css" })
public class Index {

	public enum Function {
		REVIEW, CREATE;
	}

	// The activation context

	@Property
	private Long personId;

	// Screen fields

	@Property
	private Person person;

	@Property
	private String personCreateModalId = "personCreateModal";

	// Work fields

	private Function function;

	// Generally useful bits and pieces

	@InjectComponent
	private Zone paneZone;

	@InjectComponent
	private Zone modalZone;

	@Inject
	private Request request;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Property
	private List<Person> persons;

	@InjectComponent
	private Grid grid;

	/*
	 * void onActivate(Long personId) { this.personId = personId; }
	 * 
	 * Long onPassivate() { return personId; }
	 */

	void setupRender() {
		// function = Function.REVIEW;
		populateBody();

		person = new Person((long) 1, 111, "aaa", "aaa", Regions.EAST_COAST, new Date());

		persons = new ArrayList<Person>();
		persons.add(new Person((long) 1, 111, "aaa", "aaa", Regions.EAST_COAST, new Date()));
		persons.add(new Person((long) 2, 222, "bbb", "bbb", Regions.WEST_COAST, new Date()));
		persons.add(new Person((long) 3, 333, "ccc", "ccc", Regions.EAST_COAST, new Date()));
		persons.add(new Person((long) 4, 444, "ddd", "ddd", Regions.WEST_COAST, new Date()));
		persons.add(new Person((long) 5, 555, "eee", "eee", Regions.EAST_COAST, new Date()));
		persons.add(new Person((long) 6, 666, "fff", "fff", Regions.WEST_COAST, new Date()));
		persons.add(new Person((long) 7, 777, "ggg", "ggg", Regions.EAST_COAST, new Date()));
		persons.add(new Person((long) 8, 888, "hhh", "hhh", Regions.WEST_COAST, new Date()));
		persons.add(new Person((long) 9, 999, "iii", "iii", Regions.EAST_COAST, new Date()));

		if (grid.getSortModel().getSortConstraints().isEmpty()) {
			grid.getSortModel().updateSort("startDate");
		}
	}

	// @OnEvent(value="action",component="create")
	void onToCreate(Long personId) {
		this.personId = personId;
		function = Function.CREATE;

		System.out.println("EXE ==========================================================");

		if (request.isXHR()) {
			ajaxResponseRenderer.addCallback(makeScriptToShowModal());
			ajaxResponseRenderer.addRender(modalZone);
		}
	}

	void onCanceledFromPersonCreate(Long personId) {
		// this.personId = personId;
		// function = Function.REVIEW;
		//
		// populateBody();
		//
		if (request.isXHR()) {
			ajaxResponseRenderer.addCallback(makeScriptToHideModal());
			ajaxResponseRenderer.addRender(paneZone);
		}
	}

	void onCreatedFromPersonCreate(Person person) {
		this.personId = person.getId();
		function = Function.REVIEW;

		if (request.isXHR()) {
			ajaxResponseRenderer.addCallback(makeScriptToHideModal());
			ajaxResponseRenderer.addRender(paneZone);
		}
	}

	public void populateBody() {

		// Get person with id 1 - ask business service to find it (from the
		// database).

		// person = personFinderService.findPerson(1L);
		person = new Person((long) 1, 111, "aaa", "aaa", Regions.EAST_COAST, new Date());

		if (person == null) {
			throw new IllegalStateException("Database data has not been set up!");
		}
	}

	private JavaScriptCallback makeScriptToShowModal() {
		return new JavaScriptCallback() {
			public void run(JavaScriptSupport javascriptSupport) {
				javaScriptSupport.require("simple-modal").invoke("activate").with(personCreateModalId,
						new JSONObject());
			}
		};
	}

	private JavaScriptCallback makeScriptToHideModal() {
		return new JavaScriptCallback() {
			public void run(JavaScriptSupport javascriptSupport) {
				javaScriptSupport.require("simple-modal").invoke("hide").with(personCreateModalId);
			}
		};
	}

	public boolean isFunction(Function function) {
		return function == this.function;
	}
}
