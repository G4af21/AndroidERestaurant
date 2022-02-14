package fr.isen.rouveure.androiderestaurant

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.rouveure.androiderestaurant.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    var interactor: UserActivityFragmentInteraction? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        interactor = context as? UserActivityFragmentInteraction
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            interactor?.showLogin()
        }
        binding.createButton.setOnClickListener {
            interactor?.makeRequest(
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.adress.text.toString(),
                false
            )
        }
    }

}