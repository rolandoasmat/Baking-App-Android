package com.asmat.rolando.bakingapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter
import com.asmat.rolando.bakingapp.adapters.RecipeDetailsViewPagerAdapter
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment
import com.asmat.rolando.bakingapp.fragments.RecipesListFragment
import com.asmat.rolando.bakingapp.fragments.StepsFragment
import com.asmat.rolando.bakingapp.models.Recipe
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), RecipesListFragment.OnRecipeClickListener,
        IngredientsFragment.OnIngredientsFragmentInteractionListener, StepsFragment.OnStepsFragmentInteractionListener {
    var mIsDualPane = false
    var mTabLayout: TabLayout? = null
    var mViewPager: ViewPager? = null
    var mViewPagerAdapter: RecipeDetailsViewPagerAdapter? = null
    var mSelectedRecipe: Recipe? = null

    companion object {
        val INTENT_EXTRA_RECIPE = "intent_extra_recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(R.id.activity_recipe_details_layout)
        mIsDualPane = view != null && view.visibility == View.VISIBLE
        if(mIsDualPane) {
            mTabLayout = findViewById(R.id.tab_layout)
            mViewPager = findViewById(R.id.container)
            mViewPagerAdapter = RecipeDetailsViewPagerAdapter(supportFragmentManager, this)

            mTabLayout?.setupWithViewPager(mViewPager)
        }
    }

    override fun onRecipeSelected(recipe: Recipe) {
        if(!mIsDualPane) {
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_RECIPE, recipe)
            startActivity(intent)
        } else {
            mSelectedRecipe = recipe
            setupDetailView(recipe)
        }
    }

    fun setupDetailView(recipe: Recipe) {
        supportActionBar?.title = recipe.name
        mViewPagerAdapter?.recipe = recipe
        mViewPager?.adapter = mViewPagerAdapter
        mViewPager?.adapter?.notifyDataSetChanged()
    }

    override fun onIngredientTapped(item: IngredientsAdapter.ViewHolder) {
        print(item)
    }

    override fun onBeginRecipe(view: View) {
        val intent = Intent(this, RecipeStepsActivity::class.java)
        intent.putExtra(MainActivity.INTENT_EXTRA_RECIPE, mSelectedRecipe!!)
        startActivity(intent)
    }
}