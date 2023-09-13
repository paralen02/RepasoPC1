package pe.edu.upc.aaw.demo1_202302_si63.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.aaw.demo1_202302_si63.entities.Dessert;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IDessertRepository extends JpaRepository<Dessert,Integer> {
    List<Dessert> findByDueDateDessert(LocalDate dueDateDessert);
    @Query(value = "select d.name_dessert,count(i.id_ingredient)\n" +
            " from dessert d inner join ingredient i\n" +
            " on d.id_dessert=i.id_dessert\n" +
            " group by d.name_dessert", nativeQuery = true)
public  List<String[]>quantityIngredientsByDessert();

    @Query(value = "select d.name_dessert,sum(i.amount_ingredient)\n" +
            " from dessert d inner join ingredient i\n" +
            " on d.id_dessert=i.id_dessert\n" +
            " group by d.name_dessert", nativeQuery = true)
    public  List<String[]>amountDessert();
}

