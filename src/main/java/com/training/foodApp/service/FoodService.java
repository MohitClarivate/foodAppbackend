package com.training.foodApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.training.foodApp.dao.FoodDao;
import com.training.foodApp.dto.Food;
import com.training.foodApp.dto.Menu;
import com.training.foodApp.exception.IdNotFoundException;
import com.training.foodApp.util.ResponseStructure;

@Service
public class FoodService {
	@Autowired
	FoodDao fooddao;
	
	//add food
	public Food savefood(Food food) {
		return fooddao.savefood(food);
	}
	
	//update food by id
	public ResponseEntity<ResponseStructure<Food>> updateFoodById(Food food, int id) {
		Food temp = fooddao.getFoodById(id).get();
		temp.setName(food.getName());
		temp.setPrice(food.getPrice());
		temp.setCategory(food.getCategory());
		temp.setDescription(food.getDescription());
		temp.setType(food.getType());
		temp.setAvailability(food.getAvailability());
		Food temp2 = fooddao.updateFoodById(temp, id);
        ResponseStructure<Food> structure = new ResponseStructure<Food>();
        if (temp2 != null) {
            structure.setMessage("Food Updated Successfully!");
            structure.setStatus(HttpStatus.OK.value());
            structure.setT(temp2);
            return new ResponseEntity<ResponseStructure<Food>>(structure, HttpStatus.OK);
        } else {
        	throw new IdNotFoundException();
        }
	}

	
	//find all food
	public ResponseEntity<ResponseStructure<List<Food>>> findAllFood(){
		ResponseStructure<List<Food>> structure=new ResponseStructure<List<Food>>();
		structure.setMessage("All Food Found sucessfully");
		structure.setStatus(HttpStatus.OK.value());
		structure.setT(fooddao.findAllFood());
		return new ResponseEntity<ResponseStructure<List<Food>>>(structure, HttpStatus.OK);
	}
	
	//get food by id 
	public ResponseEntity<ResponseStructure<Food>> getFoodById(int id) {
		Optional<Food> optional=fooddao.getFoodById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException();
		}else {
			ResponseStructure<Food> structure=new ResponseStructure<Food>();
			structure.setMessage("Food Found sucessfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setT(optional.get());
			return new ResponseEntity<ResponseStructure<Food>>(structure, HttpStatus.OK);
		}
	}
	
	//delete food by id
	public ResponseEntity<ResponseStructure<Food>> deleteFood(int id) {
		Food food=fooddao.deleteFood(id);
		ResponseStructure<Food> structure=new ResponseStructure<Food>();
		if(food!=null) {
			structure.setMessage("Food deleted sucessfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setT(food);
			return new ResponseEntity<ResponseStructure<Food>>(structure, HttpStatus.OK);
		}else {
			throw new IdNotFoundException();
//			structure.setMessage("invalid id");
//			structure.setStatus(HttpStatus.NOT_FOUND.value());
//			structure.setT(null);
//			return new ResponseEntity<ResponseStructure<Food>>(structure, HttpStatus.NOT_FOUND);
		}
	}


}
