import { Component, OnInit } from '@angular/core';
import {TodoserviceService} from './services/todoservice.service'
import {NgForm} from '@angular/forms';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { Todo } from './services/Todo';

@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.component.html',
  styleUrls: ['./todolist.component.css']
})
export class TodolistComponent implements OnInit
{
  closeResult = '';
  todoData:any;

  modalTaskId:string='';
  modalTaskDescr:string='';
  modalTaskComp:boolean=false;

  constructor(private todoservice: TodoserviceService, private modalService: NgbModal) { }

  ngOnInit(): void
  {
    this.todoservice.fetchTodo().subscribe(data => {
      this.todoData=data;
    });
  }

  addTask(todoForm:NgForm)
  {
    this.todoservice.addTodo(todoForm.value).subscribe(createTodo => {
      todoForm.reset();
    });
  }

  markComplete(e:any, id:string)
  {
    if(e.target.checked)
    {
      this.todoservice.completeTodo(id).subscribe(complete =>{
        //show popup and refresh list
      });
    }
  }

  deleteTask(id:string, deleteModal:any)
  {
    this.modalService.open(deleteModal, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.todoservice.deleteTodo(id).subscribe(del => {
        //Display "DELETED" popup. not done because of asynchronous behaviour of the app
      });
    }, (reason) => {
      this.getDismissReason(reason);
    });
  }

  changeTask(editTaskForm:NgForm)//called when save button clicked on modal
  {
    this.modalTaskDescr=editTaskForm.value.editedTask;
    var payLoad:Todo={
      taskid:this.modalTaskId,
      description:this.modalTaskDescr,
      completed:this.modalTaskComp
    };
    this.todoservice.editTodo(payLoad).subscribe(updateTodo => {
      this.modalTaskId='';
      this.modalTaskDescr='';
      this.modalTaskComp=false;
      editTaskForm.reset();
    });
  }

  editTask(id:string, description:string, complete:boolean, content:any)//called when edit button clicked
  {
    this.modalTaskId=id;
    this.modalTaskDescr=description;
    this.modalTaskComp=complete;

    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      //changes saved by ngSubmit calling changeTask()
    }, (reason) => {
      this.getDismissReason(reason);
    });

  }

  private getDismissReason(reason: any)
  {
    if (reason === ModalDismissReasons.ESC)
    {
    }
    else if (reason === ModalDismissReasons.BACKDROP_CLICK)
    {
    }
    else
    {
    }
  }

  refreshList()
  {
    this.todoservice.fetchTodo().subscribe(data => {
      this.todoData=data;
      console.log(data);
    });
  }
}